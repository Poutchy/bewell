import axios from "axios";

async function TakeAllSalons() {
    const url = "http://" + process.env.BACKEND_APP_HOST + "" + process.env.BACKEND_APP_PORTSERVER + "/api/salons";
    return axios.get(url).then((res) => res.data)
}

export { TakeAllSalons }