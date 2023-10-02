import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import Conformation from '../../Components/Conformation';

describe('Conformation Component', () => {

    test("Snapshot testing", () => {
        const { container } = render(<Conformation />);
    
        expect(container).toMatchSnapshot();
      });

  it('should render the component when the user is logged in', () => {
    localStorage.setItem('token', '12345');
    render(<Conformation />);
    expect(screen.getByText('Order Successful')).toBeInTheDocument();
    expect(screen.getByText('View Order Details')).toBeInTheDocument();
  });

  it('should render the component when the user is not logged in', () => {
    localStorage.removeItem('token');
    render(<Conformation />);
    expect(screen.getByText('You have to login to use this application')).toBeInTheDocument();
  });

  it('should redirect to the order details page on clicking the "View Order Details" button', () => {
    const history = createMemoryHistory();
    history.push = jest.fn();
    localStorage.setItem('token', '12345');
    render(
      <Router history={history}>
        <Conformation />
      </Router>
    );
    const viewOrderDetailsButton = screen.getByText('View Order Details');
    fireEvent.click(viewOrderDetailsButton);
    expect(history.push).toHaveBeenCalledWith({ pathname: '/orderDetails' });
  });
});
