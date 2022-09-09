<!DOCTYPE html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-6">
            <h4>{{roomName}} <span class="badge badge-info badge-pill shadow-lg">{{userCount}}</span></h4>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-primary btn-sm" href="/logout">Exit</a>
            <a class="btn btn-info btn-sm" href="/chat/room">Обратно в Холл</a>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">Содержание:</label>
        </div>
        <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage('TALK')">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage('TALK')">Sending</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
            {{message.from}} - {{message.message}}</a>
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.6.14/dist/vue.min.js"></script>
<script src="/webjars/axios/0.21.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/5.1.3/dist/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
<script>
    // websocket & stomp initialize
    var sock = new SockJS("/ws/chat");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            roomName: '',
            message: '',
            messages: [],
            token: '',
            userCount: 0
        },
        created() {
            this.roomId = localStorage.getItem('wschat.roomId');
            this.roomName = localStorage.getItem('wschat.roomName');
            var _this = this;
            axios.get('/chat/user').then(response => {
                _this.token = response.data.token;
                ws.connect({"token": _this.token}, function (frame) {
                    ws.subscribe("/topic/chat/room/" + _this.roomId, function (message) {
                        var recv = JSON.parse(message.body);
                        _this.recvMessage(recv);
                    });
                    // _this.sendMessage('ENTER');
                }, function (error) {
                    alert("Не удалось подключиться к серверу. Повторите попытку");
                    location.href = "/chat/room";
                });
            });
        },
        methods: {
            sendMessage: function (type) {
                ws.send("/app/chat/message", {"token": this.token}, JSON.stringify({
                    type: type,
                    from: this.from,
                    roomId: this.roomId,
                    message: this.message
                }));
                this.message = '';
            },
            recvMessage: function (recv) {
                this.userCount = recv.userCount;
                this.messages.unshift({"type": recv.type, "from": recv.from, "message": recv.message})
            }
        }
    });
</script>
</body>
</html>