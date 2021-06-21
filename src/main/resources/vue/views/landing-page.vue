<template id="landing-tmpl">
    <app-frame current-page="landing-page">
        <h1 class="hello-world">{{greeting}}</h1>
        <div class="pill-nav">
            <br>
            <a v-if= "landing" class="teamSelector" v-on:click="logIn">Fuck Yeah!</a>
            <br>
            <form v-if= "!landing" class="review-form" @submit.prevent="onSubmit">
                <p class = "askID" > Whats your userID?</p>

                    <input class= "inputField" v-model="uid">
                    <input class = "btn myButton" :disabled="!uid" type="submit" value="Submit">
            </form>
        </div>

<br><br><br><br><br><br>

    </app-frame>
</template>



<!-- Definition of the component and its data. Reference to the template. -->
<script>
    Vue.component("landing-comp", {
        template: "#landing-tmpl",
        data: () => ({
            landing: true,
            uid: null,
            home: "/home/",
            greeting: "Hello. Ready to enter your Virtubar?",
        }),
        methods:{
            logIn(){
                // alert("logging in!");
                this.landing= false;
            },
            onSubmit(){
                window.location.href = "/home/"+this.uid;
            }
        }
    });
</script>


<!-- Design rules for the template. -->
<style scoped>
    .hello-world  {
        color: white;
        opacity:0.9;
        text-align: center;
        font-size: 390%;
        margin: 1px;
    }

     .pill-nav {
        text-align: center;
        margin: 100px;
    } 

    .pill-nav form{
        display: inline-block;
        list-style-type: none;
        color: white;
        opacity:0.9;
        text-align: center;
        padding: 10px;
        text-decoration: none;
        font-size: 27px;
        border-radius: 20px;
    }

    .pill-nav p{
        display: inline-block;
        list-style-type: none;
        color: white;
        opacity:0.9;
        text-align: center;
        padding: 10px;
        text-decoration: none;
        font-size: 27px;
        border-radius: 20px;
    }

    .pill-nav a {
        display: inline-block;
        list-style-type: none;
        color: white;
        opacity:0.9;
        text-align: center;
        padding: 50px;
        text-decoration: none;
        font-size: 27px;
        border-radius: 20px;
    }

    .pill-nav a:hover {
        background-color: rgba(239, 0, 171, 0.17);
        color: white;
    }

    .pill-nav a.active {
        background-color: rgba(239, 0, 171, 0.17);
        color: white;
    }

    .pill-nav  .review-form{
        color: white;
    }
    .pill-nav .askID{
        color: white;
        text-align: center;
            padding: 15px;
            text-decoration: none;
            font-size: 35px;
            border-radius: 20px;
    }

    .pill-nav .submitButton{
            background-color:white;
            opacity: 0.9;
            text-align: center;
            padding: 15px;
            text-decoration: none;
            font-size: 30px;
            border-radius: 40px;
    }

    .inputField{
            background-color:white;
            text-align: center;
            padding: 10px;
            text-decoration: none;
            font-size: 20px;
            border-radius: 5px;
      }
</style>
