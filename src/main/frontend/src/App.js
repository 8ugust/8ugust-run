import React, {useEffect, useState} from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Login from './components/Login';
import './App.css';

function App() {

	const [hello, setHello] = useState('');

	return (
		<div className="App">
			<div className='body-wrap'>
				<BrowserRouter>
					<Routes>
						<Route path='/' element={<Login />}></Route>
					</Routes>
				</BrowserRouter>
			</div>	
			
		</div>
	);
}

export default App;
