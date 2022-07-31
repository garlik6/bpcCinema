# BPC Cinema

 It is a simple website for searching for movies and adding them to your watchlist(collection).
 Main goal of this project is to sharpen my knowledge of main Spring Framework modules.

## Technologies
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

![](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)![Bootstrap](https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
- Spring MVC
- Spring Security(component-based security configuration)
- Spring WebFlux
- Lombok
- Spring Data JPA
- PostgreSQL
- Themeleaf
- Bootstrap

## Important features
- [x] **Two authentication methods:**
- Basic username password 
- [OAuth2](https://datatracker.ietf.org/doc/html/rfc6749) simple sign-on with GitHub as a provider (user data is being saved to database)
- They work in parallel so user can get authenticated for reaching every secured endpoint (except admin dashboard) with one or another method.
This was achieved by configuring two different security filter chains and placing them in right order.
- [x] **Movie data loading using WebFlux webСlient**
- web client is a modern alternative to RestTemplate with async request support
- API used: filmApi.baseUrl=http://www.omdbapi.com/
- [x] **Users can add movies they liked to their collection (Many to many relationship)**
- [x] **Admin dashboard with ability to delete users and movies from database**
- cascade was configured in a way that when user is deleted movies related to him stay in a database
