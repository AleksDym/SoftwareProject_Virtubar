<template id="room-selection-tmpl">
<!--    <div class="container">-->
        <div class="row text-center">
<!--            <div v-for="room in rooms" class="col-md-2">-->
                <img v-for="room in rooms" class ="thumb" :src="stupid+room.wallCover.image" alt="step 1" @click="console.log(stupid+room.wallCover.image)">
<!--            </div>-->
<!--        </div>-->
    </div>
</template>

<!-- Definition of the component and its data. Reference to the template. -->
<script>
    Vue.component("room-selection-comp", {
        template: "#room-selection-tmpl",
        props:{
            rooms: {
                type: Array,
                required: true
            }
        },
        data: () => ({
            stupid: "../"
        }),
        created() {
        },
        computed: {

        },
        methods:{

        }
    });
</script>

<!-- Design rules for the template. -->
<style>
    .thumb {
        object-fit: cover;
        width: 173px;
        height: 173px;
    }
</style>



