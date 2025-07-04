import axios from "axios";

async function TakeAllSalons() {
    //const url = "http://" + process.env.REACT_APP_HOST + ":" + process.env.REACT_APP_PORTSERVER + "/api/salons";
    const url = "http://localhost:8080/api/salons";
    return axios.get(url).then((res) => res.data)
}

export { TakeAllSalons }