<template>
    <div>
        <h1>{{ title }}</h1>
        <form>
            <div>
                <label for="queryLocation">Location: </label>
                <input id="queryLocation" type="text" v-model="searchQuery.location">
            </div>
            <div>
                <label for="queryMilesFrom">Within how many miles? </label>
                <select id="queryMilesFrom" v-model="searchQuery.milesFrom">
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                </select>
            </div>
            <div>
                <label for="queryCategories">Categories</label>
                <select name="categories" id="queryCategories" v-model="searchQuery.categories">
                    <option value="american">American</option>
                    <option value="italian">Italian</option>
                    <option value="chinese">Chinese</option>
                </select>
            </div>
            <div>
                <label for="queryPrice">Price: </label>
                <select name="price" id="queryPrice" v-model="searchQuery.price">
                    <option value="$">$</option>
                    <option value="$$">$$</option>
                    <option value="$$$">$$$</option>
                    <option value="$$$$">$$$$</option>
                </select>
            </div>
            <button type="submit" @click.prevent="submitForm">Submit</button>
        </form>
    </div>
</template>

<script>
export default {
    name: "SearchForm",
    data() {
        return {
            title: "Restaurant Finder",
            searchQuery: {
                location: "",
                milesFrom: "",
                categories: [],
                price: ""
            }
        }
    }, 
    methods: {
        submitForm() {
            console.log(this.searchQuery.location);
        }
    }
}
</script>

<style scoped>

</style>