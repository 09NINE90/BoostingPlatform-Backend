# Backend V-Boosting
- - -
[Ссылка localhost swagger](http://localhost:6969/swagger-ui/index.html#/)

### ENV:

- JWT_SECRET_KEY=ajscqSVPNj4GNzF+Ln2H6yaE2etWGExa618+TDP96ZE=
- PASSWORD_DB=@P05tGreSQL!
- URL_DB=jdbc:postgresql://localhost:5432/boosting_platform
- USERNAME_DB=postgres

### Нагрузка
- - -

ab.exe -k -c 5 -n 5000 http://localhost:6969/api/offer/getOffersByGameId/f6a14376-2628-4a3c-9883-7ffdfb58b126

ab.exe -k -c 50 -n 500 -T "application/json" -p post-offer.json http://localhost:6969/api/offer/getOffersByRequest

ab.exe -k -c 5 -n 500 -T "application/json" -p post.json http://localhost:6969/api/auth/signIn

ab.exe -k -c 5 -n 500 -T "application/json" -p post.json http://localhost:6969/api/auth/signUp

