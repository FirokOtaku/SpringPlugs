# Gossip

## 为什么没有 Controller 层

因为 Controller 层已经涉及具体业务逻辑了.

举例说明, 不同用户的用户模块设计不同, 有的系统里用户模块可能带有标签功能,
这个时候就得把 `CompactUserService` 和 `CompactTagService` 综合使用.
但是不是所有系统都是这样.

所以目前暂时不提供任何 Controller 实现.

## 为什么扔掉了 MyBatis-Plus 相关的所有内容

因为难用, 真的难用.

MyBatis-Plus 的难用不在 API 层, 正相反, 它的 API 实属好用 (指 `QueryWrapper` 和 `UpdateWrapper`).

但是这个项目的维护和更新并不积极, 在 SpringBoot 已经升级到 3.x 的并且 MyBatis 也在积极跟进更新的今天,
每次创建新项目时引入 MyBatis-Plus 都需要浪费数小时处理各种奇奇怪怪的异常.

出于此考虑, 目前已经将 MyBatis-Plus 相关内容完全移除, 数据库交互层全面切换至 QBean 框架.
