import Header from './components/Header';
import PublicReview from './components/PublicReview'

import React from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import PersonPinIcon from '@material-ui/icons/PersonPin';
import VisibilityIcon from '@material-ui/icons/Visibility';
import ThumbUp from '@material-ui/icons/ThumbUp';
import RateReviewIcon from '@material-ui/icons/RateReview';
import Box from '@material-ui/core/Box';

const useStyles = makeStyles((theme) => ({
    '@global': {
      ul: {
        margin: 0,
        padding: 0,
        listStyle: 'none',
      },
    },
      paper: {
        padding: theme.spacing(2),
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
      },
      fixedHeight: {
        height: 500,
      },
      flexGrow: {
        flexGrow: 1,
      },
  }));

export default function Movie() {
    const classes = useStyles();
    const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

    const [state, setState] = React.useState({
        following: false,
      });
    
      const handleChange = (event) => {
        setState({ ...state, [event.target.name]: event.target.checked });
      };

    return (
        <React.Fragment>
        <CssBaseline />
            <Header />
            <Container component="main" maxWidth="lg">
                <Grid container spacing={3}>
                    {/* Profile Picture */}
                    <Grid item xs={4.5}>
                        <Paper className={fixedHeightPaper}>
                            <Card style={{width: 350, margin: 20}}>
                                <CardMedia style={{height: 400}} image="https://support.plymouth.edu/kb_images/Yammer/default.jpeg"/>
                                <CardContent>
                                </CardContent>
                            </Card>
                            <div className="title">
                              Public User
                              </div>
                        </Paper>
                    </Grid>
                    {/* Bio */}
                    <Grid item xs={7} >
                        <Paper className={fixedHeightPaper}>
                            <Typography>
                                This is some info about me and the movies I like
                            </Typography>
                            <div className={classes.flexGrow}></div>
                            <div className="right">
                                <FormControlLabel
                                  control={<Switch checked={state.seen} onChange={handleChange} name="following" color="primary"/>}
                                  label="Following"
                                 />
                            </div>
                        </Paper>
                    </Grid>
                    <Grid item xs={12}>
                        <TabButtons />
                    </Grid>
                </Grid>
            </Container>


        </React.Fragment>
    )
}

function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`scrollable-force-tabpanel-${index}`}
        aria-labelledby={`scrollable-force-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box p={3}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }
  
TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `full-width-tab-${index}`,
        'aria-controls': `full-width-tabpanel-${index}`,
    };
}

function TabButtons() {
    const classes = useStyles();
    const [value, setValue] = React.useState(0);
  
    const handleChange = (event, newValue) => {
      setValue(newValue);
    };
  
    return (
      <div className={classes.root}>
        <AppBar position="static" color="default">
          <Tabs
            value={value}
            onChange={handleChange}
            variant="fullWidth"
            indicatorColor="primary"
            textColor="primary"
            aria-label="Buton Tabs"
          >
            <Tab label="Wishlist" icon={<FavoriteIcon />} {...a11yProps(1)} />
            <Tab label="Seen" icon={<VisibilityIcon />} {...a11yProps(2)} />
            <Tab label="Reviews" icon={<RateReviewIcon />} {...a11yProps(3)} />
            
          </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
          Wishlist
        </TabPanel>
        <TabPanel value={value} index={1}>
          Seen
        </TabPanel>
        <TabPanel value={value} index={2}>
          <PublicReview />
          <PublicReview />
          <PublicReview />
        </TabPanel>
      </div>
    );
  }