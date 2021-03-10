cURL Request Example
```shell
curl --location --request POST 'http://127.0.0.1:8080/at/buy' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": "1",
    "productId":"1",
    "count":"2"
}'
```