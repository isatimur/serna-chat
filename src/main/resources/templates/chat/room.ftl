<!DOCTYPE html>
<html lang="en">
<head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
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
                <h3>List of chat rooms</h3>
            </div>
            <div class="col-md-6 text-right">
                <a class="btn btn-primary btn-sm" href="/logout">Exit</a>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
                <label class="input-group-text">Management</label>
        </div>
        <input type="text" class="form-control" v-model="room_name" v-on:keyup.enter="createRoom">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">Open chat</button>
        </div>
    </div>
    <ul class="list-group">
            <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId, item.name)">
                <h6>{{item.name}} <span class="badge badge-info badge-pill">{{item.userCount}}</span></h6>
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            room_name: '',
            chatrooms: []
        },
        created() {
            this.findAllRoom();
        },
        methods: {
            findAllRoom: function () {
                axios.get('/chat/rooms').then(response => {
                        // prevent html, allow json array
                        if(Object.prototype.toString.call(response.data) === "[object Array]")
                    this.chatrooms = response.data;
                });
            },
            createRoom: function () {
                if ("" === this.room_name) {
                    alert("Please, enter name of the room.");
                    return;
                } else {
                    var params = new URLSearchParams();
                    params.append("name", this.room_name);
                    axios.post('/chat/room', params)
                        .then(
                            response => {
                                alert(response.data.name + " You successfully opened the room.")
                                this.room_name = '';
                                this.findAllRoom();
                            }
                        )
                        .catch(response => {
                            alert("Cannot opened the room.");
                        });
                }
            },
            enterRoom: function (roomId, roomName) {
                // var from = prompt('Please, enter you login');
                // if (from != "") {
                    localStorage.setItem('wschat.roomId', roomId);
                    localStorage.setItem('wschat.roomName', roomName);
                    location.href = "/chat/room/enter/" + roomId;
                // }
            }
        }
    });
</script>
</body>
</html>