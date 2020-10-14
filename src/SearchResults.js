import Header from './components/Header';

import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import { useLocation } from "react-router-dom";

export default function SearchResults() {
    const location = useLocation();
    const title = location.state.title;

    return (
        <React.Fragment>
            <CssBaseline />
            <Header />
            <div>
                <h1>Results for "{title}"</h1>
            </div>
            {/* Call get from API */}
        </React.Fragment>
    )
}