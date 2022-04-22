import axios from 'axios';

export default {

    create(userDetails, username){
        return axios.post(`/${username}/email`, userDetails)
    }


}