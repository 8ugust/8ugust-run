import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './App.css';

function App() {

	const [hello, setHello] = useState('');

	const clickLogin = () => {
		console.log("Request");
		axios.post("/api/user/test")
			.then(response => console.log(response))
			.catch(error => console.log(error));
	}

	return (
		<div className="App">
			<div className='body-wrap'>
				<div>
					<h1>Spring Security Login Page</h1>
					<div><input type={"text"} name={"username"} placeholder={"id"}></input></div>
					<div><input type={"password"} name={"password"} placeholder={"password"}></input></div>
					<br />
					<input type={"button"} value={"Sign IN"} onClick={() => clickLogin()}></input>
				</div>
			</div>	
			
		</div>
	);
}

export default App;
