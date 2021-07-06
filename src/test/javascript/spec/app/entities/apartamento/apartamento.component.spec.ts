/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApartamentoComponent from '@/entities/apartamento/apartamento.vue';
import ApartamentoClass from '@/entities/apartamento/apartamento.component';
import ApartamentoService from '@/entities/apartamento/apartamento.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Apartamento Management Component', () => {
    let wrapper: Wrapper<ApartamentoClass>;
    let comp: ApartamentoClass;
    let apartamentoServiceStub: SinonStubbedInstance<ApartamentoService>;

    beforeEach(() => {
      apartamentoServiceStub = sinon.createStubInstance<ApartamentoService>(ApartamentoService);
      apartamentoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApartamentoClass>(ApartamentoComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          apartamentoService: () => apartamentoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      apartamentoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApartamentos();
      await comp.$nextTick();

      // THEN
      expect(apartamentoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apartamentos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      apartamentoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(apartamentoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.apartamentos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      apartamentoServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(apartamentoServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      apartamentoServiceStub.retrieve.reset();
      apartamentoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(apartamentoServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.apartamentos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      apartamentoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApartamento();
      await comp.$nextTick();

      // THEN
      expect(apartamentoServiceStub.delete.called).toBeTruthy();
      expect(apartamentoServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
