import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import axios from "axios";

export function PaymentSuccessPage() {
    const [searchParams] = useSearchParams();
    const [status, setStatus] = useState("loading");

    useEffect(() => {
        const paymentId = searchParams.get("paymentId");
        const payerId = searchParams.get("PayerID");

        if (!paymentId || !payerId) {
            setStatus("error");
            return;
        }

        axios
            .get("http://localhost:8080/api/payment/success", {
                params: { paymentId, PayerID: payerId }
            })
            .then((res) => {
                console.log("Payment success:", res.data);
                setStatus("success");
            })
            .catch((err) => {
                console.error("Payment failed:", err);
                setStatus("error");
            });
    }, [searchParams]);

    if (status === "loading") return <p>Confirming payment...</p>;
    if (status === "error") return <p>Payment failed. Please try again.</p>;

    return <h2>✅ Payment Successful! Your reservation is confirmed.</h2>;
}
