<template id="room-creation-tmpl">
    <form>
        <div class="form-group">
            <form @submit.prevent="onSubmit">
                <div class="form-group mx-sm-3 mb-2">
                <input v-model=pic class="form-control review-form" id="exampleInputEmail1" placeholder="Enter Room Image Filename">
                <button type="submit" class="btn myButton">Submit</button>
                </div>
            </form>
        </div>
</template>

<!-- Definition of the component and its data. Reference to the template. -->
<script>
    Vue.component("room-creation-comp", {
        template: "#room-creation-tmpl",
        props:{
            spaceId: {
                type: Number,
                required: true
            }
        },
        data: () => ({
            pic: null
        }),
        created() {
        },
        computed: {

        },
        methods:{
            onSubmit(){
                console.log(this.spaceId)
                console.log(this.pic)
                axios.post("/api/spaces/create_room/", {
                    "space_id":this.spaceId,
                    "image":this.pic
                }).then(response => {
                    console.log("POST successful.");  // Got a success code as response (201).
                    this.$emit('created')            // Reload spaces in parent
                    this.newCustomer = {};            // Clear input fields.
                }, error => {
                    console.error("POST failed! Error:");  // Something failed.
                    console.error(error);                  // Print error message on console.
                });
            }

        }
    });
</script>

<!-- Design rules for the template. -->
<style>

</style>



