import axios from "axios";

export async function createPaypalPayment(price) {
    const response = await axios.post("http://localhost:8080/api/payment/create", {
            "amount": price,
            "currency": "USD",
            "method": "paypal",
            "description": "Service Reservation",
            "bookingId": 1,
            "clientId": 1,
            "createdAt": "jys"
        }
    );

    return response.data;
}
