# chadwick_springboot_line
JAVA開發的 LINE訊息管理API

#使用方法
1.發送訊息

route：{url}/message/send

Method:POST

body:{

    "userid": "xxxxxxxxxxxx",

    "message": "hi user "

}

2.查詢使用者的訊息列表

route：{url}/message/{userid}

Method:GET
