# Spring Plugs

一个自用 Spring Boot 工具库, 整合各项目间的可复用或样板代码.

A personal Spring Boot utility library, includes reusable code or boilerplate code between projects.

```yaml
firok:
  spring:
    plugs:
      # 加密模块
      encrypt:
        enable: true
        private-key-path: ./private-key.bin
        public-key-path: ./public-key.bin
        private-key-value: ...
        public-key-value: ...
      # 文件模块
      file:
        enable: true
        folder-local: ./files
      # 标签模块
      tag:
        enable: true
```
