const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/rest',
    createProxyMiddleware({
      target: 'http://backend:8080',
      changeOrigin: true,
    })
  );
  app.use(
    '/filmpoker',
    createProxyMiddleware({
      target: 'ws://backend:8080/filmpoker',
    })
  )
};
