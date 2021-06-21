<template id="edit-space-tmpl">

    <div class="container spaceEdit">
        <br>
        <room-selection-comp :rooms="rooms"> </room-selection-comp>
        <room-creation-comp v-if="addRoom" v-on:created="reloadSpace" :space-id="id"> </room-creation-comp>
        <button v-if="!addRoom" @click="addRoom = true" type="button" class="btn myButton">Add Room</button>

        <p id="header"> Hey, here you can edit your space {{this.space.name}} ! </p>


        <div class="row spaceSetting">
            <div class="col-sm">
                Bar opened?
            </div>
            <div class="col-sm">
                no
            </div>
            <div class="col-sm">
                <label class="switch">
                    <input type="checkbox" @click ="toggleBarOpened()" v-if = "! isOpened">
                    <input type="checkbox" @click ="toggleBarOpened()" checked v-if = "isOpened">
                    <span class="slider round"> </span>
                </label>
            </div>
            <div class="col-sm">
                yes
            </div>
        </div>


        <div class="row spaceSetting">
            <div class="col-sm">
                Face control?
            </div>
            <div class="col-sm">
                no
            </div>
            <div class="col-sm">
                <label class="switch">
                    <input type="checkbox"  @click ="toggleFaceControl()" v-if = "! FaceControl">
                    <input type="checkbox" @click ="toggleFaceControl()" checked v-if = "FaceControl">
                    <span class="slider round"> </span>
                </label>
            </div>
            <div class="col-sm">
                yes
            </div>
        </div>
        <br>

        <form @submit.prevent="setEntryFee">
        <div class="row spaceSetting justify-content-center">
               <label for="review">Entry Fee:</label>
        </div>
        <div class="row spaceSetting justify-content-center">
            <input class="form-control review-form" id="review" v-model="entryFee">
        </div>
        <div class="row spaceSetting justify-content-center">
            <button type="submit" class="btn myButton">Submit</button>
        </div>
        </form>

    </div>

</template>
<!-- Definition of the component and its data. Reference to the template. -->
<script>
    Vue.component("edit-space-comp", {
        template: "#edit-space-tmpl",
        props:{
            id: {
                type: Number,
                required: true
            }
        },
        data: () => ({
            space : [],
            isOpened: null,
            FaceControl:null,
            addRoom: false,
            entryFee: null,
        }),
        created() {

            fetch("/api/spaces/"+this.id)
                .then(res => res.json())
                .then(res => {
                    this.space = res;
                    this.isOpened = this.space.opened;
                    this.FaceControl = this.space.faceControl;
                    this.entryFee = this.space.entryFee;
                    console.log(this.FaceControl)
                })
                .catch(() => alert("Error while fetching space"));

        },
        computed: {
            spaceDescriptor(){
                return "ID " + this.id;
            },
            rooms(){
                return this.space.rooms
            },


        },
        methods:{
            toggleBarOpened(){

                if (this.isOpened){
                    // alert("closing a bar");
                    axios.put(`/api/spaces/close_space/`+this.id, {});
                    this.isOpened = false}
                else {
                    // alert("opening a bar");
                    axios.put(`/api/spaces/open_space/`+this.id, {}); this.isOpened = true}

            },
            toggleFaceControl(){

                if (this.FaceControl){
                    // alert("switching off facecontrol");
                    axios.put(`/api/spaces/disable_face_control/`+this.id, {});
                    this.FaceControl = false}
                else {
                    // alert("switching on facecontrol");
                    axios.put(`/api/spaces/enable_face_control/`+this.id, {}); this.FaceControl = true}},
            reloadSpace(){
                this.addRoom = false;
                fetch("/api/spaces/"+this.id)
                    .then(res => res.json())
                    .then(res => {
                        this.space = res;
                    })
                    .catch(() => alert("Error while fetching space"));
            },
            setEntryFee(){
                axios.put(`/api/spaces/set_entry_fee/`+this.id, {
                    //"fee":this.entryFee
                    "fee":this.entryFee
                });
                alert("entry Fee changed :)")

            }

        }

    });
</script>

<!-- Design rules for the template. -->
<style>

    .spaceSetting{
        color: white;
        font-size: 20px;
        text-align: center;
    }

    .spaceEdit{
        border-style: solid;
        border-color: rgba( 208, 0, 189 , 0.5);
        background: black;
        background-opacity:0.7;
        border-radius: 10px;
    }
    .spaceEdit #header{
        text-align: center;
        color: white;
        font-size: 35px;
        padding: 20px;
    }
    .switch {
        text-align: center;
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
    }

    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
    }

    input:checked + .slider {
        background-color:darkmagenta;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px darkmagenta;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(26px);
        -ms-transform: translateX(26px);
        transform: translateX(26px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 34px;
    }

    .slider.round:before {
        border-radius: 50%;
    }

    .review-form {
        display:inline-block;
        width: 400px;
        padding: 20px;
        margin: 5px;
        border: 1px solid #d8d8d8;
    }

    .myButton {
        background-color: darkmagenta;
        border-radius: 10px;
        border: 2px #7a004e;
        color: white;
        padding:10px;
        margin: 30px;
    }

    .myButton:hover {
        background-color: #7a004e;
        border-radius: 10px;
        border: 2px #7a004e;
        color: antiquewhite;
        padding:10px;
        margin: 30px;
    }

    .myButton:active {
        background-color: #631547;
    }

</style>



