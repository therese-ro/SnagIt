describe('Login form', () => {
  it('Should send if all fields are correctly filled', () => {
    cy.visit('/login');

    cy.intercept('POST', 'login').as('apiRequest');

    cy.get('#email').type('c@mail.com');
    cy.get('#password').type('SecurePassword123');

    cy.get('form#loginForm').submit();

    cy.wait('@apiRequest', {timeout: 1000}).should('exist');
  });
});
