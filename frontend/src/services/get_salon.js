import axios from "axios";

async function TakeSalon(id) {
    const url = "http://" + process.env.BACKEND_APP_HOST + ":" + process.env.BACKEND_APP_PORTSERVER + "/api/salons/" + String(id);
    return axios.get(url).then((res) => res.data)
}

export { TakeSalon }