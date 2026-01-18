@echo off
echo ========================================
echo COMPLETE API ENDPOINT TEST
echo Testing: POST, GET by ID, and SEARCH
echo ========================================
echo.

set API_KEY=local-test-api-key-12345
set BASE_URL=http://localhost:8081

echo [STEP 1] Checking Application Health...
curl -s %BASE_URL%/actuator/health
echo.
echo.

echo [STEP 2] Current Database Count...
curl -s %BASE_URL%/debug/contacts/count
echo.
echo.

echo ========================================
echo TESTING POST ENDPOINTS (Create Contacts)
echo ========================================
echo.

echo [STEP 3] POST - Creating Contact 1: Alice Johnson
curl -X POST "%BASE_URL%/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: %API_KEY%" ^
  -d "{\"firstName\":\"Alice\",\"lastName\":\"Johnson\",\"address1\":\"789 Pine Street\",\"zipcode\":\"67890\",\"city\":\"Utrecht\",\"iban\":\"NL02RABO0123456789\",\"dateOfBirth\":\"1992-05-10\",\"email\":\"alice@example.com\",\"phone\":\"+31-30-9876543\"}"
echo.
echo.

echo [STEP 4] POST - Creating Contact 2: Bob Smith
curl -X POST "%BASE_URL%/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: %API_KEY%" ^
  -d "{\"firstName\":\"Bob\",\"lastName\":\"Smith\",\"address1\":\"123 Main Street\",\"zipcode\":\"12345\",\"city\":\"Amsterdam\",\"iban\":\"NL91ABNA0417164300\",\"dateOfBirth\":\"1985-03-15\",\"email\":\"bob@example.com\",\"phone\":\"+31-20-1234567\"}"
echo.
echo.

echo [STEP 5] POST - Creating Contact 3: Carol Williams
curl -X POST "%BASE_URL%/contacts" ^
  -H "Content-Type: application/json" ^
  -H "X-API-KEY: %API_KEY%" ^
  -d "{\"firstName\":\"Carol\",\"lastName\":\"Williams\",\"address1\":\"456 Oak Avenue\",\"zipcode\":\"54321\",\"city\":\"Rotterdam\",\"iban\":\"NL20INGB0001234567\",\"dateOfBirth\":\"1990-07-22\",\"email\":\"carol@example.com\"}"
echo.
echo.

echo ========================================
echo TESTING GET BY ID ENDPOINTS (Read Individual Contact)
echo ========================================
echo.

echo [STEP 6] GET - Retrieving Contact ID 1
curl -X GET "%BASE_URL%/contacts/1" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 7] GET - Retrieving Contact ID 2
curl -X GET "%BASE_URL%/contacts/2" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 8] GET - Retrieving Contact ID 3
curl -X GET "%BASE_URL%/contacts/3" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 9] GET - Testing Non-Existent Contact ID 999
curl -X GET "%BASE_URL%/contacts/999" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo ========================================
echo TESTING SEARCH ENDPOINTS (Query Contacts)
echo ========================================
echo.

echo [STEP 10] SEARCH - By First Name: Alice
curl -X GET "%BASE_URL%/contacts?firstname=Alice" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 11] SEARCH - By Last Name: Smith
curl -X GET "%BASE_URL%/contacts?lastname=Smith" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 12] SEARCH - By City (using first name + last name): Carol Williams
curl -X GET "%BASE_URL%/contacts?firstname=Carol&lastname=Williams" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo [STEP 13] SEARCH - By IBAN
curl -X GET "%BASE_URL%/contacts?iban=NL91ABNA0417164300" ^
  -H "X-API-KEY: %API_KEY%"
echo.
echo.

echo ========================================
echo VIEWING DATABASE CONTENTS
echo ========================================
echo.

echo [STEP 14] All Contacts in Database (via Debug Endpoint)
curl -s %BASE_URL%/debug/contacts/all
echo.
echo.

echo [STEP 15] Final Contact Count
curl -s %BASE_URL%/debug/contacts/count
echo.
echo.

echo ========================================
echo TEST COMPLETE!
echo ========================================
echo.
echo Check the console where you ran "java -jar target\contacts.jar"
echo to see detailed logs for EVERY endpoint call:
echo.
echo Expected Logs:
echo   - POST requests: "POST /contacts endpoint HIT!"
echo   - GET by ID: "GET /contacts/{id} endpoint HIT!"
echo   - SEARCH: "GET /contacts (search) endpoint HIT!"
echo   - API authentication logs
echo   - Database operation logs
echo.
echo ========================================
echo Access Points:
echo ========================================
echo Swagger UI: %BASE_URL%/webjars/swagger-ui/index.html
echo View All DB: %BASE_URL%/debug/contacts/all
echo Count DB: %BASE_URL%/debug/contacts/count
echo Health Check: %BASE_URL%/actuator/health
echo API Key: %API_KEY%
echo ========================================
pause

