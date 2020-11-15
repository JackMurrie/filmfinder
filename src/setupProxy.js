const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/rest',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
    })
  );
  app.use(
    '/filmpoker',
    createProxyMiddleware({
      target: 'ws://localhost:8080/filmpoker',
    })
  )
};
