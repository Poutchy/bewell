import axios from "axios";

async function TakeAllSalons() {
    const url = "http://" + process.env.BACKEND_APP_HOST + ":" + process.env.BACKEND_APP_PORTSERVER + "/api/salons";
    //return axios.get(url).then((res) => res.data)
    return [{
        address: "123 Main Street, Cityville",
        description: "A high-end salon offering premium services.",
        email: "contact@luxurysalon.com",
        imageUrls: ["https://example.com/image1.jpg","https://example.com/image2.jpg"],
        latitude: 40.7128,
        longitude: -74.006,
        name: "Excellency Royal VIP Salon",
        openingHours: {Monday: "9:00 AM - 7:00 PM",Tuesday: "9:00 AM - 7:00 PM",Wednesday: "9:00 AM - 7:00 PM",Thursday: "9:00 AM - 7:00 PM",Friday: "9:00 AM - 8:00 PM",Saturday: "10:00 AM - 6:00 PM",Sunday: "Closed"},
        phone: "+1234567890",
        pricingRange: "$$$",
        rating: 4.8,
        socialMediaLinks: "https://www.facebook.com/luxurysalon,https://www.instagram.com/luxurysalon",
        totalReviews: 150,
        website: "https://www.luxurysalon.com",
        services: [
            {
                id: 1,
                name: "Haircut Royale",
                description: "A stylish haircut tailored to your preferences.",
                price: 150,
                duration: "PT30M",
                discount: 10.0,
                tags: [
                    {
                        id: 0
                    }
                ],
                available: true
            }
        ],
    },{
        address: "123 Main Street, Cityville",
        description: "A high-end salon offering premium services.",
        email: "contact@luxurysalon.com",
        imageUrls: ["https://example.com/image1.jpg","https://example.com/image2.jpg"],
        latitude: 40.7128,
        longitude: -74.006,
        name: "Excellency Royal VIP Salon",
        openingHours: {Monday: "9:00 AM - 7:00 PM",Tuesday: "9:00 AM - 7:00 PM",Wednesday: "9:00 AM - 7:00 PM",Thursday: "9:00 AM - 7:00 PM",Friday: "9:00 AM - 8:00 PM",Saturday: "10:00 AM - 6:00 PM",Sunday: "Closed"},
        phone: "+1234567890",
        pricingRange: "$$$",
        rating: 4.8,
        socialMediaLinks: "https://www.facebook.com/luxurysalon,https://www.instagram.com/luxurysalon",
        totalReviews: 150,
        website: "https://www.luxurysalon.com",
        services: [
            {
                id: 1,
                name: "Haircut Royale",
                description: "A stylish haircut tailored to your preferences.",
                price: 150,
                duration: "PT30M",
                discount: 10.0,
                tags: [
                    {
                        id: 0
                    }
                ],
                available: true
            }
        ],
    }]
}

export { TakeAllSalons }