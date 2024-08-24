import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api/v1/restaurant-summary': {
        target: 'http://localhost:8080/api/v1/restaurant-summary',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/restaurant-summary/, '')
      },
      '/api/v1/restaurants': {
        target: 'http://localhost:7002/api/v1/restaurants',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/restaurants/,
            '')
      },
      '/api/v1/menus': {
        target: 'http://localhost:7002/api/v1/menus',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/v1\/menus/, '')
      }
    }
  }
})
