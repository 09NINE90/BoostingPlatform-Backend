# Backend V-Boosting
- - -
- [Ссылка localhost swagger](http://localhost:6969/swagger-ui/index.html#/)
- [Ссылка на yaml doc](http://localhost:6969/boosting-platform/api-docs.yaml)
### Нагрузка
- - -

ab.exe -k -c 5 -n 5000 http://localhost:6969/api/offer/getOffersByGameId/f6a14376-2628-4a3c-9883-7ffdfb58b126

ab.exe -k -c 50 -n 500 -T "application/json" -p post-offer.json http://localhost:6969/api/offer/getOffersByRequest

ab.exe -k -c 5 -n 500 -T "application/json" -p post.json http://localhost:6969/api/auth/signIn

ab.exe -k -c 5 -n 500 -T "application/json" -p post.json http://localhost:6969/api/auth/signUp

