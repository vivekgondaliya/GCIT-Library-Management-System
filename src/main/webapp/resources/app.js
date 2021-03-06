var libraryModule = angular.module('libraryModule', [ 'ngRoute', 'ngCookies', 'ui.bootstrap', 'ngAnimate']);

//ngRoute : directive helps achieve SINGLE PAGE APPLICATION.

libraryModule.config(function($routeProvider) {

	$routeProvider

	.when("/", {
		redirectTo : "/home"
	})
	.when("/home", {
		templateUrl : "home.html"
	})

	.when("/adminServices",{
		templateUrl: "adminServices.html"
	})

	.when("/librarianServices",{
		templateUrl: "librarianServices.html"
	})

	.when("/borrowerServices",{
		templateUrl: "borrowerServices.html"
	})


	.when("/addAuthor", {
		templateUrl : "addAuthor.html",
		controller: 'authorCtrl'
	})
	.when("/listAuthors", {
		templateUrl : "listAuthors.html",
		controller: 'authorCtrl'
	})
	.when("/editAuthor", {
		templateUrl : "editAuthor.html",
		controller: 'authorCtrl'
	})


	.when("/addBook", {
		templateUrl : "addBook.html",
		controller: 'bookCtrl'
	})
	.when("/listBooks", {
		templateUrl : "listBooks.html",
		controller: 'bookCtrl'
	})


	.when("/addPublisher", {
		templateUrl : "addPublisher.html",
		controller: 'publisherCtrl'
	})
	.when("/listPublishers", {
		templateUrl : "listPublishers.html",
		controller: 'publisherCtrl'
	})


	.when("/addBorrower", {
		templateUrl : "addBorrower.html",
		controller: 'borrowerCtrl'
	})
	.when("/listBorrowers", {
		templateUrl : "listBorrowers.html",
		controller: 'borrowerCtrl'	
	})

	.when("/addGenre", {
		templateUrl : "addGenre.html",
		controller: 'genreCtrl'
	})
	.when("/listGenres", {
		templateUrl : "listGenres.html",
		controller: 'genreCtrl'
	})

});

//pagination
libraryModule.filter('startFrom', function(){
	return function(data, start){
		if (!data || !data.length) { return; }
		return data.slice(start);
	}
});

//carousel
libraryModule.controller('homeController', ['$scope', function($scope){
	$scope.myInterval = 3000;
	$scope.slides = [{
		image:"./img/angular.png",
		link: "https://angularjs.org/"
	},
	{
		image:"./img/bootstrap.png",
		link:"http://getbootstrap.com/"
	},
	{
		image:"./img/mongo.png",
		link:"https://www.mongodb.org/"
	},
	]

}]);

//AUTHOR
libraryModule.controller('authorCtrl', ['$rootScope','$scope','$http','$modal', '$log',
                                        function( $rootScope, $scope, $http, $modal, $log) {

	$scope.authors = [];
	$http.get('http://localhost:8080/lms/authors/get').success(function(data) {
		$scope.authors = data;
	})
	.error(function(data, status){
		console.log(data);
	});

	//data Order By
	$scope.orderProp = 'authorId';

	//pagination
	$scope.pageSize= 5;
	$scope.currentPage = 1;

	//add Author
	$scope.addAuthor = function(){
		$http.post('http://localhost:8080/lms/author/add', {authorName: $scope.insertAuthor}).success(function(data) {
			console.log(data);
			$scope.authors.push(data);
		})
		.error(function(data, status){
			console.log(data);
		});
		//empty the field
		$scope.insertAuthor= '';
	}

	//edit Author
	$scope.editAuthor = function(authorId){
		console.log("Update name: " + authorId);
		$http.post('http://localhost:8080/lms/author/getOne', {'authorId':authorId}).success(function(data) {
			$rootScope.author = data;
			window.location.href="http://localhost:8080/lms/#/editAuthor";
		})
		.error(function(data, status){
			console.log(data);
		});
		//angular.element(document.getElementById("modal")).scope().a = a;
	}

	//finalEdit
	$scope.finalEditAuthor = function(){
		$http.post('http://localhost:8080/lms/author/edit', {
			'authorName': $scope.author.authorName,
			'authorId': $scope.author.authorId
		}).success(function(data){
			console.log(data);
			$scope.authors = data;
		});

		window.location.href="http://localhost:8080/lms/#/listAuthors";
	}

	//deleteAuthor
	$scope.deleteAuthor = function(index){
		$http.post('http://localhost:8080/lms/author/delete', {authorId: $scope.authors[index].authorId}).success(function(data) {
			console.log("Author Deleted");
			$scope.authors.splice(index, 1);
		})
		.error(function(data, status){
			console.log(data);
		});
	}

}]);

//BOOK
libraryModule.controller('bookCtrl', function( $scope, $http, $cookieStore) {
	$scope.books=[];
	$scope.publishers= [];
	$scope.authors = [];
	$scope.genres= [];
	//get all authors and display initially
	$http.get('http://localhost:8080/lms/books/get').success(function(data) {
		$scope.books = data;	
	});

	//authors
	$http.get('http://localhost:8080/lms/authors/get').success(function(data) {
		$scope.authors = data;
	})
	.error(function(data, status){
		console.log(data);
	});
	//genres
	$http.get('http://localhost:8080/lms/genres/get').success(function(data) {
		$scope.genres = data;
	})
	.error(function(data, status){
		console.log(data);
	});
	//publishers
	$http.get('http://localhost:8080/lms/publishers/get').success(function(data) {
		$scope.publishers = data;
	})
	.error(function(data, status){
		console.log(data);
	});

	//add Book
	$scope.addBook = function(){
		console.log($scope.insertTitle);
		console.log($scope.insertPublisher);
		console.log($scope.insertAuthor);
		console.log($scope.insertGenre)
		$scope.selected=[];
		$scope.$watch('selected', function(nowSelected){
			$scope.insertAuthor =[];
			if(!nowSelected){
				return;
			}
			angular.forEach(nowSelected, function(value, key){
				$scope.insertAuthor.push(key+':' +value);
			});
		});
		
		$scope.$watch('selected', function(nowSelected){
			$scope.insertGenre =[];
			if(!nowSelected){
				return;
			}
			angular.forEach(nowSelected, function(value, key){
				$scope.insertGenre.push(key+':' +value);
			});
		});	
		$scope.publishers.push($scope.insertPublisher);
		
		
		$http.post('http://localhost:8080/lms/book/add', {
			'title': $scope.insertTitle,
			'publisher':$scope.insertPublisher,
			'authors': $scope.insertAuthor,
			'genres': $scope.insertGenre
		}).success(function(data) {
			console.log(data);
			$scope.books.push(data);
		})
		.error(function(data, status){
			console.log(data);
		});
		//empty the field
		$scope.insertAuthor= '';
		$scope.insertTitle= '';
		$scope.insertGenre= '';
		$scope.insertPublisher= '';
	}
	
	//deleteAuthor
	$scope.deleteBook = function(index){
		$http.post('http://localhost:8080/lms/book/delete', {
			'bookId': $scope.books[index].bookId
			}).success(function(data) {
			console.log("Book Deleted");
			$scope.books.splice(index, 1);
		})
		.error(function(data, status){
			console.log(data);
		});
	}
});

//PUBLISHER
libraryModule.controller('publisherCtrl', function( $scope, $http, $cookieStore) {
	
	$scope.publishers = [];
	//data Order By
	$scope.orderProp = 'publisherId';
	//pagination
	$scope.pageSize= 5;
	$scope.currentPage = 1;
	
	//get all authors and display initially
	$http.get('http://localhost:8080/lms/publishers/get').success(function(data) {
		$scope.publishers = data;	
	})
	.error(function(data, status){
		console.log(data);
	});;

	$scope.addPublisher = function(){	
		$http.post('http://localhost:8080/lms/publisher/add', {publisherName: $scope.publisherName, publisherAddress: $scope.publisherAddress, publisherPhone: $scope.publisherPhone})
		.success(function(data) {
			$scope.publishers.push(data);
		})
		.error(function(data, status){
			console.log(data);
		});;
		//empty the field
		$scope.publisherName= '';
		$scope.publisherAddress= '';
		$scope.publisherPhone= '';
	};
	
	//delete Publisher
	$scope.deletePublisher = function(index){
		$http.post('http://localhost:8080/lms/publisher/delete', {
			'publisherId': $scope.publishers[index].publisherId
			}).success(function(data) {
			console.log("Pub Deleted");
			$scope.publishers.splice(index, 1);
		})
		.error(function(data, status){
			console.log(data);
		});
	}
});

//BORROWER
libraryModule.controller('borrowerCtrl', function( $scope, $http, $cookieStore) {
	
	$scope.borrowers = [];
	$http.get('http://localhost:8080/lms/borrowers/get').success(function(data) {
		$scope.borrowers = data;	
	})
	.error(function(data, status){
		console.log(data);
	});

	//data Order By
	$scope.orderProp = 'cardNo';

	//pagination
	$scope.pageSize= 5;
	$scope.currentPage = 1;

	//add Borrower
	$scope.addBorrower = function(){	
		$http.post('http://localhost:8080/lms/borrower/add', {
			'name': $scope.borrowerName, 
			'address': $scope.borrowerAddress,
			'phone': $scope.borrowerPhone
			})
			.success(function(data) {
				$scope.borrowers.push(data);
			})
		.error(function(data, status){
			console.log(data);
		});;
		//empty the field
		$scope.borrowerName= '';
		$scope.borrowerAddress= '';
		$scope.borrowerPhone= '';
	};

	//deleteBorrower
	$scope.deleteBorrower = function(index){
		$http.post('http://localhost:8080/lms/borrower/delete', {cardNo: $scope.borrowers[index].cardNo}).success(function(data) {
			console.log("Borrower Deleted");
			$scope.borrowers.splice(index, 1);
		})
		.error(function(data, status){
			console.log(data);
		});
	};
});

//GENRE
libraryModule.controller('genreCtrl', ['$scope','$http', function( $scope, $http) {
	
	$scope.genres =[];
	$scope.orderProp = 'genreId';
	//pagination
	$scope.pageSize= 5;
	$scope.currentPage = 1;
	
	$http.get('http://localhost:8080/lms/genres/get').success(function(data) {
		$scope.genres = data;
	});
	
	//add Genre
	$scope.addGenre = function(){
		$http.post('http://localhost:8080/lms/genre/add', {genreName: $scope.insertGenre}).success(function(data) {
			console.log(data);
			$scope.genres.push(data);
		});
		//empty the field
		$scope.insertGenre= '';
	};
	
	//delete Genre
	$scope.deleteGenre = function(index){
		$http.post('http://localhost:8080/lms/genre/delete', {genreId: $scope.genres[index].genreId}).success(function(data) {
			$scope.genres.splice(index, 1);
		})
		.error(function(data, status){
			console.log(data);
		});
	}
}]);

libraryModule.controller('navController', ['$scope','$location',function($scope, $location){
	$scope.isActive = function(destination){
		return destination === $location.path();
	}
}]);
