# COS 375: Hash-Cracker
## Teddy Jandro

See [hash-rest](./hash-rest) for the serverside Spring RESTful web service and [hash-cracker-app](./hash-cracker-app) for the clientside Angular web application.

### Current State

Currently, the REST API is fully implemented on the Spring side, with backing by MySQL.

On the Angular side, I have the use case covered where a user provides the passwords plaintext and requests the hash and metadata associated. I have also implemented the use case where a user provides a hash value and requests the plaintext of the password which it was generated from. Both these implementations are only partly working, but they are functional for happy paths.

See [output](./output) for screen shots of the REST API, the MySQL tables backing the REST API, and the Angular front-end.


### Original Project Proposition: 

  For my project I would like to extend a previous project I did for the Ethics of Computing class. For that project, I attempted my first web application: a brute force password cracker that runs totally in browser. It tries to randomly guess plaintext passwords to crack an LM Hash, which is known to be the easiest password hashing scheme to crack. 
  I successfully completed the project, but the solution was very inelegant and inefficient, not to mention ugly. It's actually still running at cs.usm.maine.edu/~jandro so you can see how far I got. It's written completely by hand in JavaScript and basic HTML. I have a pretty good discussion of what could be improved about the password cracker itself functionally, but I also think it would be interesting to take the opportunity in this class to make it more visually appealing via Angular, as well as more efficient via REST.
  In my discussion I talk about efficiency concerns, one of which is to have persistency of a map of previously checked plaintext to the  hashes themselves. This could easily be implemented with a REST API. 
  
User Stories:
  As a user, I can provide a hash with a known encryption scheme so that I can recover the plaintext password associated with it.
	
  As a user, I can provide a hash with its known plaintext so that the collection of known hashes can be expanded
	
  As a user, I can provice a hash with an unknown encryption scheme so that I can recover the plaintext password associated with it.

Server Data:
  The server attached to the Angular project will provide a REST API which will be responsible for doing the heavy-lifting of the hash cracking. From the Angular client's perspective the REST API will simply provide a plaintext password as a response to a request containing a hash. Some metadata will also be transferred, for example: when the hash was generated, how quickly it was found in the database, how many times it has been requested, etc. On the actual backend I plan to have the server maintain some persistent map of hash, plaintext pairs. This map will grow with time, based on users either providing it (through user story 2) or by it being generated. 
  If time permits I would like to support multiple different hashing algorithms which are available, but to start I will limit it to the LM Hashing algorithm. The mechanisms of the map is another hurdle I will need to overcome. I can most easily implement a simple lookup table, but after some research I've found that rainbow tables provide better memory conservation at the cost of computation time. 
