describe('Register form', () => {
  it('Should send if all fields are correctly filled', () => {
    cy.visit('/register');

    cy.intercept('POST', 'register').as('apiRequest');

    cy.get('#displayName').type('Test Name');
    cy.get('#email').type('c@mail.com');
    cy.get('#password').type('SecurePassword123');

    cy.get('form#registerForm').submit();

    cy.wait('@apiRequest', {timeout: 1000}).should('exist');
  });
});
