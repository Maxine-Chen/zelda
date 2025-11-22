# ZELDA 微信小程序

这是一个基于微信小程序和Spring Boot的电商应用，包含内容编辑功能。

## 功能特性

### 1. 内容编辑器（易次元风格编辑器）

新增的内容编辑器允许登录用户创建和编辑富文本内容。该编辑器的设计灵感来自易次元编辑器，提供了丰富的内容创作功能。

#### 主要功能：

- **富文本编辑**：使用微信小程序原生的editor组件，支持富文本格式编辑
- **图片上传**：支持选择和上传封面图片
- **标题管理**：可设置内容标题
- **链接设置**：可选的链接地址配置
- **内容预览**：在保存前预览内容
- **保存功能**：创建新内容或更新现有内容

#### 使用方法：

1. **创建新内容**：
   - 在首页登录后，点击右下角的悬浮按钮（+）
   - 进入编辑器页面
   - 填写标题、上传封面图、编辑内容
   - 点击"保存"按钮

2. **编辑现有内容**：
   - 在内容详情页点击编辑按钮
   - 修改标题、图片或内容
   - 点击"保存"按钮

#### 技术实现：

**前端（微信小程序）：**
- 页面位置：`/pages/editor/editor`
- 使用微信小程序的editor组件
- 支持图片上传和富文本编辑
- 与后端API集成

**后端（Spring Boot）：**
- Controller: `ContentController.java`
- 新增接口：
  - `GET /appinfo/content/getById` - 获取单个内容详情
  - `POST /appinfo/content/add` - 创建新内容
  - `POST /appinfo/content/update` - 更新内容
  - `POST /appinfo/content/delete` - 删除内容
- Upload Controller: `UploadController.java`
  - `POST /appinfo/upload/image` - 上传图片

## 项目结构

```
zelda/
├── pages/                    # 小程序页面
│   ├── index/               # 首页
│   ├── editor/              # 内容编辑器（新增）
│   ├── content/             # 内容详情
│   ├── shop/                # 商店
│   ├── car/                 # 购物车
│   ├── order/               # 订单
│   └── ...
├── src/main/java/           # 后端代码
│   └── com/pmx/zelda_java/
│       └── appinfo/
│           ├── controller/  # 控制器
│           ├── entity/      # 实体类
│           ├── service/     # 服务层
│           └── mapper/      # 数据访问层
└── pom.xml                  # Maven配置
```

## 技术栈

**前端：**
- 微信小程序
- WeChat Editor组件

**后端：**
- Java 17
- Spring Boot 3.5.5
- MyBatis Plus 3.5.12
- MySQL

## 开发指南

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- 微信开发者工具

### 后端启动

```bash
# 克隆项目
git clone https://github.com/Maxine-Chen/zelda.git

# 进入项目目录
cd zelda

# 配置数据库连接（src/main/resources/application.yml）

# 编译运行
./mvnw spring-boot:run
```

### 前端开发

1. 使用微信开发者工具打开项目根目录
2. 配置小程序appid（project.config.json）
3. 在app.js中配置后端baseUrl
4. 预览或调试

## 注意事项

1. 编辑器功能需要用户登录后才能使用
2. 图片上传需要配置服务器上传路径（application.properties中的upload.path）
3. 确保数据库中有content表并包含相应字段

## 许可证

本项目仅供学习和参考使用。
