# Test all endpoints of the Contacts API
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Testing Contacts API Endpoints" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$apiKey = "cb4e56e7-712d-4899-a8ed-a5f7d9b938ca"
$baseUrl = "http://localhost:8081"
$headers = @{
    "Content-Type" = "application/json"
    "X-API-KEY" = $apiKey
}

# Test 1: POST - Create Contact 1
Write-Host "Test 1: POST /contacts - Create first contact" -ForegroundColor Yellow
$contact1 = @{
    firstName = "Alice"
    lastName = "Johnson"
    address1 = "789 Pine Street"
    zipcode = "67890"
    city = "Utrecht"
    iban = "NL02RABO0123456789"
    dateOfBirth = "1992-05-10"
    email = "alice.johnson@example.com"
    phone = "+31-30-9876543"
} | ConvertTo-Json

try {
    $response1 = Invoke-RestMethod -Uri "$baseUrl/contacts" -Method POST -Headers $headers -Body $contact1
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($response1 | ConvertTo-Json -Compress)" -ForegroundColor Gray
    $contact1Id = $response1.id
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 2: POST - Create Contact 2
Write-Host "Test 2: POST /contacts - Create second contact" -ForegroundColor Yellow
$contact2 = @{
    firstName = "Bob"
    lastName = "Smith"
    address1 = "123 Main Street"
    zipcode = "12345"
    city = "Amsterdam"
    iban = "NL91ABNA0417164300"
    dateOfBirth = "1985-03-15"
    email = "bob.smith@example.com"
    phone = "+31-20-1234567"
} | ConvertTo-Json

try {
    $response2 = Invoke-RestMethod -Uri "$baseUrl/contacts" -Method POST -Headers $headers -Body $contact2
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($response2 | ConvertTo-Json -Compress)" -ForegroundColor Gray
    $contact2Id = $response2.id
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 3: POST - Create Contact 3
Write-Host "Test 3: POST /contacts - Create third contact" -ForegroundColor Yellow
$contact3 = @{
    firstName = "Carol"
    lastName = "Williams"
    address1 = "456 Oak Avenue"
    zipcode = "54321"
    city = "Rotterdam"
    iban = "NL20INGB0001234567"
    dateOfBirth = "1990-07-22"
    email = "carol.williams@example.com"
} | ConvertTo-Json

try {
    $response3 = Invoke-RestMethod -Uri "$baseUrl/contacts" -Method POST -Headers $headers -Body $contact3
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($response3 | ConvertTo-Json -Compress)" -ForegroundColor Gray
    $contact3Id = $response3.id
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Wait a moment for data to be persisted
Start-Sleep -Seconds 2

# Test 4: GET by ID
if ($contact1Id) {
    Write-Host "Test 4: GET /contacts/$contact1Id - Get contact by ID" -ForegroundColor Yellow
    try {
        $getResponse = Invoke-RestMethod -Uri "$baseUrl/contacts/$contact1Id" -Method GET -Headers $headers
        Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
        Write-Host "Response: $($getResponse | ConvertTo-Json -Compress)" -ForegroundColor Gray
    } catch {
        Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
    }
    Write-Host ""
}

# Test 5: GET by firstName
Write-Host "Test 5: GET /contacts?firstname=Alice - Search by first name" -ForegroundColor Yellow
try {
    $searchResponse1 = Invoke-RestMethod -Uri "$baseUrl/contacts?firstname=Alice" -Method GET -Headers $headers
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($searchResponse1 | ConvertTo-Json -Compress)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 6: GET by lastName
Write-Host "Test 6: GET /contacts?lastname=Smith - Search by last name" -ForegroundColor Yellow
try {
    $searchResponse2 = Invoke-RestMethod -Uri "$baseUrl/contacts?lastname=Smith" -Method GET -Headers $headers
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($searchResponse2 | ConvertTo-Json -Compress)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

# Test 7: GET by city
Write-Host "Test 7: GET /contacts?firstname=Carol&lastname=Williams - Search by multiple criteria" -ForegroundColor Yellow
try {
    $searchResponse3 = Invoke-RestMethod -Uri "$baseUrl/contacts?firstname=Carol&lastname=Williams" -Method GET -Headers $headers
    Write-Host "✓ Status: SUCCESS" -ForegroundColor Green
    Write-Host "Response: $($searchResponse3 | ConvertTo-Json -Compress)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Status: FAILED - $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "All Tests Completed!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

