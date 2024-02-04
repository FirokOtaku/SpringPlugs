# Spring Plugs

一个自用 Spring Boot 工具库, 整合各项目间的可复用或样板代码.

A personal Spring Boot utility library,
includes reusable code or boilerplate code between projects.

虽然是开源的, 但是不对项目内容做任何保证, 所有内容均可能频繁变更且不提供变更信息.

Although it is open source,
it does not guarantee any project content,
and all content may change frequently and does not provide changelog information.

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
        auto-table-creation: true
      # 标签模块
      tag:
        enable: true
        auto-table-creation: true
      # 用户模块
      user:
        enable: true
        auto-table-creation: true
```
