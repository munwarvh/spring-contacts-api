@echo off
echo ========================================
echo Complete API Test with Database Verification
echo ========================================
echo.

set API_KEY=local-test-api-key-12345
set BASE_URL=http://localhost:8081

echo [1/6] Checking Application Health...
curl -s %BASE_URL%/actuator/health
echo.
echo.

echo [2/6] Checking Current Database Count...
curl -s %BASE_URL%/debug/contacts/count
echo.
echo.

echo [3/6] Creating Contact 1 (Alice Johnson)...
curl -X POST "%BASE_URL%/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: %API_KEY%" ^
  -d "{\"firstName\":\"Alice\",\"lastName\":\"Johnson\",\"address1\":\"789 Pine Street\",\"zipcode\":\"67890\",\"city\":\"Utrecht\",\"iban\":\"NL02RABO0123456789\",\"dateOfBirth\":\"1992-05-10\",\"email\":\"alice@example.com\",\"phone\":\"+31-30-9876543\"}"
echo.
echo.

echo [4/6] Creating Contact 2 (Bob Smith)...
curl -X POST "%BASE_URL%/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: %API_KEY%" ^
  -d "{\"firstName\":\"Bob\",\"lastName\":\"Smith\",\"address1\":\"123 Main Street\",\"zipcode\":\"12345\",\"city\":\"Amsterdam\",\"iban\":\"NL91ABNA0417164300\",\"dateOfBirth\":\"1985-03-15\",\"email\":\"bob@example.com\"}"
echo.
echo.

echo [5/6] Viewing All Contacts in Database...
curl -s %BASE_URL%/debug/contacts/all
echo.
echo.

echo [6/6] Final Contact Count...
curl -s %BASE_URL%/debug/contacts/count
echo.
echo.

echo ========================================
echo Test Complete!
echo.
echo Check the console where you ran java -jar
echo to see the detailed logs showing:
echo - Request received logs
echo - API Key authentication logs
echo - Controller method hit logs
echo - Database operation logs
echo ========================================
echo.
echo Additional Endpoints:
echo - Swagger UI: %BASE_URL%/webjars/swagger-ui/index.html
echo - View All DB: %BASE_URL%/debug/contacts/all
echo - Count DB: %BASE_URL%/debug/contacts/count
echo - Health: %BASE_URL%/actuator/health
echo ========================================
pause

