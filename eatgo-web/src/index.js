(async () => {
	const url = "http://localhost:8080/restaurants";
	const response = await fetch(url);
	const restaurants = await response.json();

	const element = document.getElementById('app');
	// element.innerHTML = JSON.stringify(restaurants);
	console.log(restaurants);

	element.innerHTML = 
	`
	    <table border="3" width="500" height="300">
	        <th>ID</th>
	        <th>Name</th>
	        <th>City</th>
	        <th>Category</th>
                ${restaurants.map(restaurant => `
                    <tr align = "center">
                        <td> ${restaurant.id}  </td>
                        <td> ${restaurant.name} </td>
                        <td> ${restaurant.city} </td>
                        <td width="80"> ${restaurant.categoryId} </td>
                    </tr>
                `).join('')}
        </table>
	`;
})();