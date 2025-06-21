package com.miniprogram_frame.gateway;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

//👉 把网关路由中的 httpbin 地址替换为本地 WireMock 地址，完全脱离真实外部 HTTPBin。
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "httpbin=http://localhost:${wiremock.server.port}" })

// 👉 自动启动一个 WireMock 本地 HTTP 服务器，端口随机。
@AutoConfigureWireMock(port = 0)

class GatewayApplicationTests {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void contextLoads() throws Exception {
    // Stubs
    // 👉 伪造 HTTPBin 的 /get 请求，返回自定义 JSON。
    stubFor(get(urlEqualTo("/get"))
        .willReturn(aResponse()
            .withBody("{\"headers\":{\"Hello\":\"World\"}}")
            .withHeader("Content-Type", "application/json")));

    // 👉 伪造 HTTPBin 的 /delay/3 请求，故意延迟 3 秒，强制触发断路器。
    stubFor(get(urlEqualTo("/delay/3"))
        .willReturn(aResponse()
            .withBody("no fallback")
            .withFixedDelay(3000)));

    // 👉 模拟客户端请求 /get，验证路由和请求头是否生效。
    webClient
        .get().uri("/get")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.headers.Hello").isEqualTo("World");

    // 👉 模拟客户端请求 /delay/3，验证断路器是否跳转到了 /fallback，响应是否为 "fallback"。
    webClient
        .get().uri("/delay/3")
        .header("Host", "www.circuitbreaker.com")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(
            response -> assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));

  }

}

