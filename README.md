This project demonstrates how basic auth mechanism could be used for microservices built using Spring Boot could synchronously communicate. To strengthen the mechanism, every password is encrypted and then hashed. So on every incoming request, the implementation matches the hash and then decrypt the password. The implementation is modular enough to choose any algorithms.