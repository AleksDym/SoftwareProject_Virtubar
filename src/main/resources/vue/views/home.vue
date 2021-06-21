<template id="home-tmpl">
    <app-frame current-page="home/1">
        <h1 v-if="state === 'home'" class="hello-world">{{greeting}} </h1>
        <div class="pill-nav">
            <a :class="{active :(state === 'home')}" href="#home" v-on:click="goHome">Home</a>
            <a :class="{active :(state === 'space')}"href="#Spaces" v-on:click="setShowSpaces">MY SPACES</a>
            <a href="#about">EDIT PROFILE</a>
            <a href="/">LogOut</a>
        </div>

        <div class="container spaceSelector" v-if= "showSpaces">
            <div class="teamSelector row" v-for="(space, index) in spaces"
                     :class="{selectedSpaceListItem : ((hoveredSpace === space.spaceId) || (selectedSpace === space.spaceId)),
                              spaceListItem: (hoveredSpace !== space.spaceId),}"
                     v-on:click="editSpace(space)"
                     @mouseover="highlightSpace(space.spaceId)"
                     @mouseout="highlightSpace(0)">

                    {{space.name}}
            </div>
            <br>
            <div class="row">
                <edit-space-comp :key="editSpaceKey"
                                 v-bind:id="selectedSpace"
                                 v-if="selectedSpace > 0">
                </edit-space-comp>
            </div>
        </div>
    </app-frame>
</template>


<!-- Definition of the component and its data. Reference to the template. -->
<script>
    Vue.component("home-comp", {
        props:{
            userID:{
                type:Number,
                required: true
            }
        },
        template: "#home-tmpl",
        data: () => ({
            user: null,
            firstName: null,
            showSpaces : false,
            hoveredSpace:0,
            selectedSpace: 0,
            spaces: [],
            state: "home",
            editSpaceKey:0
        }),
        created() {
            const userId = this.$javalin.pathParams["user-id"];
            this.user = userId;
            fetch("/api/customers/"+this.user)
                .then(res => res.json())
                .then(res => {
                    this.firstName = res.firstName;
                    this.spaces = res.spaces;
                })
                .catch(() => alert("Error while fetching customers"));

        },
        computed: {
            numSpaces(){
                return 2;
            },
            greeting(){
                return "Hello, "+ this.firstName + "!";
            }
        },
        methods:{
            setShowSpaces(){
                this.showSpaces= true;
                this.state = "space"
            },
            goHome(){
                this.showSpaces= false;
                this.state = "home"
                this.selectedSpace = 0;
            },
            editSpace(space){
                this.selectedSpace = space.spaceId;
                this.editSpaceKey +=1;
                //alert(space.spaceId);
            },
            highlightSpace(spaceId){
                this.hoveredSpace = spaceId;
            }
        }

    });
</script>


<!-- Design rules for the template. -->
<style>

    .spaceSelector {
        text-align: center;
        font-size: 40px;
    }
.spaceListItem{
    color: white;
}

.selectedSpaceListItem{
    color: magenta;
}

.showSpacesPill-nav{
    text-align: center;
    padding-bottom:100px;
    line-height: 76pt;
    font-size: 40px;
    max-width: 550px;
    padding: 10px;
    position: center;
    border-radius: 25px;
}
     </style>



