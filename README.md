# 中文说明

# lang-note app接口说明
### Api BaseUrl
#### http://116.196.107.16:8080/note



##### **注意：所有需要添加cookie的接口所需要的token由==登录接口==通过==cookie==返回，userId由==登录接口==通过==json==返回。**

---

# 用户接口
## Get
### ==登录==

请求地址：http://116.196.107.16:8080/note/login

参数 | 类型| 长度|必填|说明
---|---|---|---|---
userId | String | 20|yes|用户Id
userPwd| String| 20 |yes |用户的密码

返回示例


返回Cookie

```
userId   bigfly
token    d04939f7d0d3ae5a4cdc13d693d3d421
```

JSON
 ``` 
{
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "bigfly",
        "userName": "大飞",
        "userSex": 0,
        "userProfile": "大家好，我叫大飞，我是一枚小处男",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg",
    }
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
1 | 登录失败，用户名或密码错误|无
15| 该用户不存在|无
---

### ==获取个人信息==

请求地址：http://116.196.107.16:8080/note/info

参数 | 类型| 长度|必填|说明
---|---|---|---|---
无|无|无|无|无

请求Cookie

```
userId   "value"
token    "value"
```

返回示例

JSON
 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "bigfly",
        "userName": "大飞",
        "userSex": 0,
        "userProfile": "大家好，我叫大飞，我是一枚小处男",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg"
    }
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
15| 该用户不存在|无


---

## Post
### ==注册==

请求地址：http://116.196.107.16:8080/note/register


参数 | 类型| 长度|必填|说明
---|---|---|---|---
userId | String | 20|yes|用户Id
userName |String | 20|yes |用户的显示名字
userPwd| String| 20 |yes |用户的密码
userProfile |String | 100|no |用户的个人简介
userSex |int |1 |no |用户性别:0男1女

返回示例

JSON

 ```
 {
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "user1",
        "userName": "langenius",
        "userSex": 0,
        "userProfile": "我是一只小小鸟",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg"
    }
}
```
错误返回值

错误代码 |返回信息|说明
---|---|--
5 | 用户名已存在|无
6 | 用户ID参数必传|无
7 | 用户名参数必传|无
8 | 用户密码参数必传|无

---

### ==修改密码==

请求地址：http://116.196.107.16:8080/note/updatePwd

参数 | 类型| 长度|必填|说明
---|---|---|---|---
oldPwd |String |20 |yes |旧密码
newPwd |String |20 |yes |新密码

请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
 {
    "code": 0,
    "msg": "成功"
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
3 | 旧密码不正确|无
4 | 用户权限校验失败|无
6 | 用户ID参数必传|无
8 | 用户密码参数必传|无
9 | 用户新密码参数必传|无
10 | token参数必传|无
15| 该用户不存在|无

---

### ==更新个人简介==

请求地址：http://116.196.107.16:8080/note/updateProfile

参数 | 类型| 长度|必填|说明
---|---|---|---|---
userProfile| String | 100|yes |用户个人简介

请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
 {
    "code": 0,
    "msg": "成功"
}
```
错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
11|个人简介参数必传|无
15| 该用户不存在|无

---

# 日记接口
## Get

### ==获取日记==

请求地址：http://116.196.107.16:8080/note/getNote

参数 | 类型| 长度|必填|说明
---|---|---|---|---
page |int|无 | no |分页，默认值1
size |int|无 |no |页内数量，默认值10

请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "noteId": 12,
            "noteContent": "这是个测试的文本内容",
            "noteTitle": "这是个测试标题",
            "updateTime": 1515725192000
        },
        {
            "noteId": 9,
            "noteContent": "啦啦啦啦啦，我是卖菊花的小二飞",
            "noteTitle": "这是被更新的数据，id是9",
            "updateTime": 1515723309000
        },
        {
            "noteId": 11,
            "noteContent": "这是我的测试内容内容内容",
            "noteTitle": "标题测试",
            "updateTime": 1515719234000
        }
    ]
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
15| 该用户不存在|无

---

## Post
### ==添加日记==

请求地址：http://116.196.107.16:8080/note/addNote

参数 | 类型| 长度|必填|说明
---|---|---|---|---
noteTitle |String | 50|yes |日记标题
noteContent |String |1000|yes |日记正文

请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "noteId": 14,
        "noteContent": "测试内容",
        "noteTitle": "测试标题",
        "updateTime": 1515742927123
    }
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
12 | 标题参数必传|无
13 | 内容参数必传|无
15| 该用户不存在|无


---

### ==更新日记==

请求地址：http://116.196.107.16:8080/note/updateNote

参数 | 类型| 长度|必填|说明
---|---|---|---|---
noteId |Long|不限制| yes |日记的id
noteTitle |String | 50|yes |日记标题
noteContent |String |1000|yes |日记正文

请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "noteId": 8,
        "noteContent": "测试内容",
        "noteTitle": "测试标题",
        "updateTime": 1515743092874
    }
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
12 | 标题参数必传|无
13 | 内容参数必传|无
14 | noteId参数必传|无
15| 该用户不存在|无

---
### ==删除日记==

请求地址：http://116.196.107.16:8080/note/deleteNote

参数 | 类型| 长度|必填|说明
---|---|---|---|---
noteId |Long|不限制| yes |日记id


请求Cookie

```
userId   "value" 
token    "value"
```

返回示例

JSON

 ```
{
    "code": 0,
    "msg": "成功"
}
```

错误返回值

错误代码 |返回信息|说明
---|---|--
4|用户token校验失败|无
6 | 用户ID参数必传|无
10 | token参数必传|无
14 | noteId参数必传|无
15| 该用户不存在|无


# English


# lang-note app api document
### Api BaseUrl
#### http://116.196.107.16:8080/note



##### **Warning：All the token required to add the cookie interface is returned by the login interface through the cookie, and the userId is returned by the login interface through the JSON**

---

# User Api
## Get
### ==Login==

request url：http://116.196.107.16:8080/note/login

param | type| length|not null|description
---|---|---|---|---
userId | String | 20|yes|user's id
userPwd| String| 20 |yes |user's password

return


response cookie

```
userId   bigfly
token    d04939f7d0d3ae5a4cdc13d693d3d421
```

JSON
 ``` 
{
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "bigfly",
        "userName": "大飞",
        "userSex": 0,
        "userProfile": "大家好，我叫大飞，我是一枚小处男",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg",
    }
}
```

error code

code |info|description
---|---|--
1 | 登录失败，用户名或密码错误(Login failed)|none
15| 该用户不存在(user is not exist)|none
---

### ==get userInfo==

request url：http://116.196.107.16:8080/note/info

param | type| length|not null|description
---|---|---|---|---
none|none|none|none|none

Cookie

```
userId   "value"
token    "value"
```

return

JSON
 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "bigfly",
        "userName": "大飞",
        "userSex": 0,
        "userProfile": "大家好，我叫大飞，我是一枚小处男",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg"
    }
}
```

error code 

code |info|description
---|---|--
4|用户token校验失败(token error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
15| 该用户不存在(user is not exist)|none


---

## Post
### ==register==

request url：http://116.196.107.16:8080/note/register


param | type| length|not null|description
---|---|---|---|---
userId | String | 20|yes|user's id
userName |String | 20|yes |user's nickname
userPwd| String| 20 |yes |user's password
userProfile |String | 100|no |user's profile
userSex |int |1 |no |user's sex 0:male 1:female

return

JSON

 ```
 {
    "code": 0,
    "msg": "成功",
    "data": {
        "userId": "user1",
        "userName": "langenius",
        "userSex": 0,
        "userProfile": "我是一只小小鸟",
        "avatarUrl": "https://pic2.zhimg.com/50/v2-d757e91a15dde9792a1850aed2f1a1c8_hd.jpg"
    }
}
```
error code

code |info|description
---|---|--
5 | 用户名已存在(the user is exist)|none
6 | 用户ID参数必传(userId is needed)|none
7 | 用户名参数必传(userName is needed)|none
8 | 用户密码参数必传(userPwd is needed)|none

---

### ==update password==

request url：http://116.196.107.16:8080/note/updatePwd

param | type| length|not null|description
---|---|---|---|---
oldPwd |String |20 |yes |the old password
newPwd |String |20 |yes |the new password

request cookie

```
userId   "value" 
token    "value"
```

return

JSON

 ```
 {
    "code": 0,
    "msg": "成功"
}
```

error code

type |info|description
---|---|--
4|用户token校验失败(token check error)|none
3 | 旧密码不正确(the old password is not correct)|none
4 | 用户权限校验失败(user token check error)|none
6 | 用户ID参数必传(userId is needed)|none
8 | 用户密码参数必传(user old password is needed)|none
9 | 用户新密码参数必传(user new password is needed)|none
10 | token参数必传(token is needed)|none
15| 该用户不存在(the user is not exist)|none

---

### ==update personal profile==

request url：http://116.196.107.16:8080/note/updateProfile

param | type| length|not null|description
---|---|---|---|---
userProfile| String | 100|yes |user's profile

request cookie

```
userId   "value" 
token    "value"
```

return 

JSON 

 ```
 {
    "code": 0,
    "msg": "成功"
}
```
error code

type |info|description
---|---|--
4|用户token校验失败(token check error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
11|个人简介参数必传(personal profile is needed)|none
15| 该用户不存在(the user is not exist)|none

---

# note api
## Get

### ==get note==

request url：http://116.196.107.16:8080/note/getNote

param | type| length|not null|description
---|---|---|---|---
page |int| none| no |page，default page 1
size |int|none |no |page size，default size 10

request cookie

```
userId   "value" 
token    "value"
```

return

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "noteId": 12,
            "noteContent": "这是个测试的文本内容",
            "noteTitle": "这是个测试标题",
            "updateTime": 1515725192000
        },
        {
            "noteId": 9,
            "noteContent": "啦啦啦啦啦，我是卖菊花的小二飞",
            "noteTitle": "这是被更新的数据，id是9",
            "updateTime": 1515723309000
        },
        {
            "noteId": 11,
            "noteContent": "这是我的测试内容内容内容",
            "noteTitle": "标题测试",
            "updateTime": 1515719234000
        }
    ]
}
```

error code

type |info|description
---|---|--
4|用户token校验失败(token check error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
15| 该用户不存在(the user is not exist)|none

---

## Post
### ==add note==

request url：http://116.196.107.16:8080/note/addNote

param | type| length|not null|description
---|---|---|---|---
noteTitle |String | 50|yes |title of note
noteContent |String |1000|yes |content of note

request cookie

```
userId   "value" 
token    "value"
```

return

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "noteId": 14,
        "noteContent": "测试内容",
        "noteTitle": "测试标题",
        "updateTime": 1515742927123
    }
}
```

error code


type |info|description
---|---|--
4|用户token校验失败(token check error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
12 | 标题参数必传(title is needed)|none
13 | 内容参数必传(content is needed)|none
15| 该用户不存在(the user is not exist)|none



---

### ==update note==

request url：http://116.196.107.16:8080/note/updateNote

param | type| length|not null|description
---|---|---|---|---
noteId |Long|none| yes |id of note
noteTitle |String | 50|yes |title of note
noteContent |String |1000|yes |content of note

request cookie

```
userId   "value" 
token    "value"
```

return

JSON

 ```
{
    "code": 0,
    "msg": "成功",
    "data": {
        "noteId": 8,
        "noteContent": "测试内容",
        "noteTitle": "测试标题",
        "updateTime": 1515743092874
    }
}
```

error code


param | type| length|not null|description
---|---|--
4|用户token校验失败(token check error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
12 | 标题参数必传(title is needed)|none
13 | 内容参数必传(content is needed)|none
14 | noteId参数必传(note id is needed)|none
15| 该用户不存在(the user is not exist)|none




---
### ==delete note==

request url：http://116.196.107.16:8080/note/deleteNote

param | type| length|not null|description
---|---|---|---|---
noteId |Long|none| yes |id of note


request cookie

```
userId   "value" 
token    "value"
```

return

JSON

 ```
{
    "code": 0,
    "msg": "成功"
}
```

error code


param | type| length|not null|description
---|---|--
4|用户token校验失败(token check error)|none
6 | 用户ID参数必传(userId is needed)|none
10 | token参数必传(token is needed)|none
14 | noteId参数必传(note id is needed)|none
15| 该用户不存在(the user is not exist)|none


