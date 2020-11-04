import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Link from '@material-ui/core/Link';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import CardContent from '@material-ui/core/CardContent';

import { useAsync } from 'react-async';
import { CardActions } from '@material-ui/core';

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
      right: {
        textAlign: 'right',
      },
      control: {
        marginLeft: 'auto'
      }
    }));

export default function PrivateReview(props) {
    const classes = useStyles();

    const requestOptions = {
      method: 'DELETE',
      headers: { 
        'Accept': 'application/json',
      }
    };
    
    const removeReviewAndReload = async () => {
      await fetch(`/rest/review/${props.movieId}`, requestOptions);

      props.onChange();
    };

    const deleteReview = useAsync({ deferFn: removeReviewAndReload });

    return (
        <Grid item xs={12}>
            <Card style={{width: 1150, margin: 10, backgroundColor: "DarkGrey"}}>
              <CardHeader
                title={<Link href={`/Movie/${props.movieId}`} color="primary" className={classes.link} style={{ fontSize: '30px' }}>{props.title}</Link>}
              />
            <CardContent>
              {props.text}
            </CardContent>
            <CardActions className={classes.action}>
              {/* TODO: implement an edit review functionalilty from the user dashboard */}
              <IconButton color="primary" component="span" className={classes.control}>
                <EditIcon />
              </IconButton>
              <IconButton color="primary" component="span" className={classes.control} onClick={deleteReview.run}>
                <DeleteIcon />
              </IconButton>
            </CardActions>
          </Card>
        </Grid>
    );
  }