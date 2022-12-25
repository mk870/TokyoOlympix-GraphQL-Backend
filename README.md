# Tokyo Olympix GraphQL  Server (Backend)
<img src="https://i.ibb.co/Hdq55GW/spring.jpg" alt="spring" border="0" width="400" align="center"> 

## Project Summary 
* This  is a GraphQL Spring boot application with CRUD operations that serves as a backend for the tokyo olympix Data Visualization app.
* It allows users to create an account , save or add video comments, add likes and dislikes , delete those comments and likes and then login and out using spring security.
* It uses  Spring boot with Tomcat server as a framework.
* The app has 1 endpoint facilitating 16 GraphQL mappings, 10 mutations  and 6 queries.
* The API uses Spring security and Jwt to secure this endpoint.
* Last but not least it uses spring boot data jpa (hibernate) to persist data to a postgreSQL database.


### **Resources Used**
***
**Java Version**: 17

**Dependencies**: Jwt Token, Hibernate, Spring security, Java Mail,Spring GraphQL, TomcatServer, lombok, postgreSQL-connector and Spring web.  
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=flat&logo=spring&logoColor=white) 	![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=JSON%20web%20tokens) 	![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=flat&logo=postgresql&logoColor=white) ![GraphQL](https://img.shields.io/badge/-GraphQL-E10098?style=flat&logo=graphql&logoColor=white)

**For Web Framework Requirements**: pom.xml

**External APIs**: None

### **GraphQL Queries and Mutation Building**
***
Built 3 Controllers, UserController, CommentsController and LikesController.
#### **User Account Creation :** 
* **saveuser (Mutation)**: Takes in firstname, lastname, password and email for user signup. A Jwt token is created as an authentication tool, its stored on the database and also sent by java mail to user email for verification. The password is encrypted using BCryptPasswordEncoder.

* **verifyRegistration  (Mutation)**: validates the email token against the one on the database, once verified the account is enabled. 
* **userlogin  (Mutation)**: A Jwt token is created and returned if user login credentials are valid. 


#### **Video Comments :**  
* **saveComment (Mutation)**:  saves users' comments to the database using one to many relationship. 
* **commentsByEvent (Query)**:  retrieves all the saved comments by their olympic event from the database.
* **commentsByVideoId (Query)**:  retrieves all the saved comments by their video id from the database.
* **editComment(id) (Mutation)** : allows users to edit their comments.
* **deleteComment(id) (Mutation)** : allows users to delete their specific comments by id from the database.

#### **Video Likes and DisLikes:**  
* **addLike (Mutation)**:  saves users' video likes to the database using one to many relationship. 
* **LikesByEvent (Query)**:  retrieves all the saved likes by their olympic event from the database.
* **LikesByVideoId (Query)**:  retrieves all the saved likes by their video id from the database.
* **deleteLike(id) (Mutation)** : allows users to delete their specific video likes by id from the database. 
* **addUnLike (Mutation)**:  saves users' video dislikes to the database using one to many relationship. 
* **UnLikesByEvent (Query)**:  retrieves all the saved dislikes by their olympic event from the database.
* **UnLikesByVideoId (Query)**:  retrieves all the saved dislikes by their video id from the database.
* **deleteUnLike(id) (Mutation)** : allows users to delete their specific video dislikes by id from the database. 

### **Data Storage**
The backend uses Spring data JPA (Hibernate) to persist and retrieve data from a postgreSQL database.  
The API has 5 entities: 
* User Entity to store app users.
* VerificationToken Entity to store signup verification tokens.
* Comments Entity to store video comments of users. 
* Likes Entity to store video likes of users.
* UnLikes Entity to store video dislikes of users.



### **Productionization**
***
In this step I deployed the postgreSQL database to AWS and deployed the springboot app on Railway cloud.

**Live Implemantation:** [Tokyo Olympix App](https://tokyo-olympix.vercel.app)
