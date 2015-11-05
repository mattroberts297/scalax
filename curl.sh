curl --request "PUT" --header "Content-Type: application/json" --data "@patient.json" \
"http://127.0.0.1:8080/patients/2cf50f4d-9a15-49ac-8a37-b343de5d0442"

curl --request "GET" --header "Accept: application/json" \
"http://127.0.0.1:8080/patients/2cf50f4d-9a15-49ac-8a37-b343de5d0442"

ab -m "GET" -n 100 -c 10 \
"http://127.0.0.1:8080/patients/2cf50f4d-9a15-49ac-8a37-b343de5d0442"
