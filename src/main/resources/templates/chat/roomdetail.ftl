<!DOCTYPE html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div>
        <h2>{{room.name}}</h2>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">Содержание:</label>
        </div>
        <input type="text" class="form-control" v-model="message" @keyup.enter="sendMessage">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="sendMessage">Sending</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item" v-for="message in messages">
            {{message.from}} - {{message.message}}</a>
        </li>
    </ul>
    <div></div>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    // websocket & stomp initialize
    var sock = new SockJS("/ws/chat");
    var ws = Stomp.over(sock);
    // vue.js
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: '',
            room: {},
            from: '',
            message: '',
            messages: []
        },
        created() {
            alert('created')
            this.roomId = localStorage.getItem("wschat.roomId");
            this.from = localStorage.getItem("wschat.from");
            alert('roomId: ' + this.roomId)
            alert('from: ' + this.from)
            this.findRoom();
        },
        methods: {
            findRoom: function () {
                axios.get('/chat/room/' + this.roomId).then(response => {
                    this.room = response.data;
                });
            },
            sendMessage: function () {
                ws.send("/app/chat/message", {}, JSON.stringify({
                    type: 'TALK',
                    roomId: this.roomId,
                    from: this.from,
                    message: this.message
                }));
                this.message = '';
            },
            recvMessage: function (recv) {
                this.messages.unshift({
                    "type": recv.type,
                    "from": recv.type == 'ENTER' ? '[notification]' : recv.from,
                    "message": recv.message
                })
            }
        }
    });
    // pub/topic event
    ws.connect({}, function(frame) {
        ws.subscribe("/topic/chat/room/"+vm.$data.roomId, function(message) {
            var recv = JSON.parse(message.body);
            vm.recvMessage(recv);
        });

        ws.send("/app/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, from:vm.$data.from}));
        alert(vm.$data.roomId)
        alert(vm.$data.from)

    }, function(error) {
        alert("error "+error);
    });
</script>
</body>
</html>