import { Button } from "@mui/material";
import { Box } from "@mui/material";
import { Component } from "react";

/**
 * 
 * @param {} prop 
 * @returns 
 */
const MuiButton = ({text, clickEvent}) => {

    const styled_component = {
        background: 'linear-gradient(120deg, #89f7fe 0%, #66a6ff 100%)',
        borderRadius:'30px', 
        fontWeight:'bold', 
        fontSize:'15px', 
        height: '50px',
        width: '70%',
        color:'white',
        border:'0px'
    };

    return (<>
        <Box style={{paddingBottom:'10px'}}>
            <Button style={styled_component} onClick={() => clickEvent()}>{text || 'Text'}</Button>
        </Box>
    </>)
}

export default MuiButton;