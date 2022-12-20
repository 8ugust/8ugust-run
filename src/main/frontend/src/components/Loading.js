import {CircularProgress, Box} from '@material-ui/core'
import { useContext } from 'react';
import { Context } from "../App";

const Loading = () => {
    const global = useContext(Context).global;

    return (
        <Box sx={{ display: 'flex' }} style={{position:'absolute', width:'500px', paddingTop:'calc((100%-40px)/2)', display:(global.onLoad ? 'block' : 'none')}}>
            <CircularProgress style={{position:'relative', color:'white'}}/>
        </Box>
    );
}

export default Loading;

