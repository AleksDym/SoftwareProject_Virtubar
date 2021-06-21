<template id="app-frame">
    <div class="app-frame">
        <header>
            <img src="../name.png" id="logo" alt="Virtubar Demo App Logo" style="width:200px;height:200px;"class="image"/>
        </header>
        <slot>
            <!-- Hier wird automatisch der restliche Code des Komponenten-Templates eingefügt! -->
        </slot>
        <footer>
            created by  Aleksandra Dymchishina &amp; Matthias Mildenberger<br />
            &copy; All rights reserved
        </footer>
    </div>
</template>

<script>
    Vue.component("app-frame", {
        template: "#app-frame",
        props: ['currentPage'],
        data: () => ({
        }),
        // mounted: function() {
        //     // Wenn der DOM fertig ist, setzen wir den Link der aktuellen Seite auf aktiv.
        //     this.$refs["nav-"+this.currentPage].classList.add("active");
        // },
        methods: {
        }
    });
</script>

<style>
    @media (min-width: 700px) {
        .container {
            width: 700px;
        }
    }

    .teamSelector
    {
        cursor: pointer
    }

    .image{
        display: block;
        /*margin-left: auto;*/
        /*margin-right: auto;*/
        width: 50%;
}
    .app-frame > header {
        padding: 20px;
        font-size: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    #navigation li {
        display: inline;
    }

    footer {
        border-top: 1px ;/*solid lightgrey;*/
        margin-top: 15px;
        padding: 25px;
        line-height: normal;
        font-size: 20px;
        color:rgb(128,128,128);
        opacity:0.4;
        text-align: center;
        font-style: italic;
    }


    p {
        margin: 0 0 1em 0;
    }

    #logo {
        float: left;
    }

    body {
        font-family: "Helvetica", cursive, sans-serif;
        color: white;
        background: rgb(0,0,0);
        background: linear-gradient(180deg, rgba(0,0,0,1) 35%, rgba(54,23,49,1) 72%, rgba(89,36,81,1) 100%);
    }

    h1{
        font-family: "Georgia", cursive, serif;
    }

</style>
