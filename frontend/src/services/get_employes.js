import axios from "axios"

async function TakeAllEmployees(salon_id) {
    return [
        {
            id: 1,
            name: "Suzette",
        },
        {
            id: 2,
            name: "Clémentine",
        },
        {
            id: 3,
            name: "Joséphine",
        },
        {
            id: 4,
            name: "Jasmine",
        },
    ]
}

export { TakeAllEmployees };