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
        stubs: { bModal: bModalStub as any },
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
