import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import { useContext } from 'react';
import { Context } from "../App";

const Loading = () => {
    const global = useContext(Context).global;

    const styled_component = {
        display: (global.onLoad ? 'block' : 'none'),
        position: 'absolute',
        background: 'black',
        height: '100vh',
        width: '100vw',
        opacity:'50%',
        zIndex: 999,
    }

    return (
        <Box sx={{ display: 'flex' }} style={styled_component}>
            <Box style={{paddingTop:'50vh', zIndex:1000}}>
                <CircularProgress style={{position:'relative', color:'white'}}/>
            </Box>
        </Box>
    );
}

export default Loading;

