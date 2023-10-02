// src/mocks/handlers.js
import { rest } from "msw";

export const handlers = [
  rest.post("http://localhost:8080/users/signin", (req, res, ctx) => {
    return res(
      // Respond with a 200 status code
      ctx.status(200),
      ctx.body("Successful")
    );
  }),

  rest.post("http://localhost:8080/users/createUser", (req, res, ctx) => {
    return res(
      // Respond with a 200 status code
      ctx.status(200),
      ctx.body("Registered")
    );
  }),

  rest.get("http://localhost:8080/users/name/*", async (req, res, ctx) => {
    return res(
      // Respond with a 200 status code
      ctx.status(200),
      ctx.body("Aryan")
    );
  }),

  rest.get("http://localhost:8080/orders/getOrder/*", (req, res, ctx) => {
    return res(
      // Respond with a 200 status code
      ctx.status(200),
      ctx.json({
        orderName: "Aryan",
        items: {
          orderItems: {
            rajma: "2",
            chicken: "3",
          },
        },
        cost: "2000",
      })
    );
  }),

  rest.put("http://localhost:8080/users/updateUser/*", (req, res, ctx) => {
    return res(ctx.status(200), ctx.body("User Updated"));
  }),
  
];
